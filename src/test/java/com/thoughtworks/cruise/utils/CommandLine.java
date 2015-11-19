/********************************************************************************
 * CruiseControl, a Continuous Integration Toolkit
 * Copyright (c) 2001-2003, ThoughtWorks, Inc.
 * 200 E. Randolph, 25th Floor
 * Chicago, IL 60601 USA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     + Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     + Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 *     + Neither the name of ThoughtWorks, Inc., CruiseControl, nor the
 *       names of its contributors may be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ********************************************************************************/
/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Ant", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package com.thoughtworks.cruise.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Commandline objects help handling command lines specifying processes to execute.
 * <p/>
 * The class can be used to define a command line as nested elements or as a helper to define a command line by an
 * application.
 * <p/>
 * <code>
 * &lt;someelement&gt;<br>
 * &nbsp;&nbsp;&lt;acommandline executable="/executable/to/run"&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;argument value="argument 1" /&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;argument line="argument_1 argument_2 argument_3" /&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;argument value="argument 4" /&gt;<br>
 * &nbsp;&nbsp;&lt;/acommandline&gt;<br>
 * &lt;/someelement&gt;<br>
 * </code> The element <code>someelement</code> must provide a method <code>createAcommandline</code> which returns
 * an instance of this class.
 *
 * @author thomas.haas@softwired-inc.com
 * @author <a href="mailto:stefan.bodewig@epost.de">Stefan Bodewig</a>
 */
public class CommandLine {

    private static final Logger LOG = Logger.getLogger(CommandLine.class);

    public static final String CRUISE_KILLABLE_PROCESS_PROPERTY = "cruise.use.killable.process";

    private final String executable;
    private final List<CommandArgument> arguments = new ArrayList<CommandArgument>();

    private File workingDir = null;
    private Map<String, String> env = new HashMap<String, String>();
    private List<CommandArgument> secrets = new ArrayList<CommandArgument>();
    private List<String> inputs = new ArrayList<String>();
    private String encoding;
    public static final long NO_TIMEOUT = -1;

    private CommandLine(String executable) {
        this.executable = executable;
    }


    private void addStringArguments(String... args) {
        for (String arg : args) {
            arguments.add(new StringArgument(arg));
        }
    }

    protected File getWorkingDir() {
        return workingDir;
    }

    public Map<String, String> env() {
        return env;
    }

    public String describe() {
        String description = "--- Command ---\n" + toString()
                + "\n--- Environment ---\n" + env + "\n"
                + "--- INPUT ----\n" + StringUtils.join(inputs, ",") + "\n";
        for (CommandArgument argument : arguments) {
            description = argument.replaceSecretInfo(description);
        }

        for (CommandArgument argument : secrets) {
            description = argument.replaceSecretInfo(description);
        }
        return description;
    }

    /**
     * Returns the executable and all defined arguments.
     */
    private String[] getCommandLine() {
        List<String> args = new ArrayList<String>();
        if (useKillableProcess()) {
            addExecutable(args, killableProcess());
        }
        if (executable != null) {
            addExecutable(args, executable);
        }
        for (int i = 0; i < arguments.size(); i++) {
            CommandArgument argument = arguments.get(i);
            args.add(argument.forCommandline());
        }
        return args.toArray(new String[args.size()]);
    }

    private void addExecutable(List<String> args, String executable) {
        File file = new File(workingDir, executable);
        if (file.exists() && file.canExecute()) {
            args.add(file.getAbsolutePath());
        } else {
            args.add(executable);
        }
    }

    private String[] getCommandLineForDisplay() {
        List<String> args = new ArrayList<String>();
        if (executable != null) {
            args.add(executable);
        }
        for (int i = 0; i < arguments.size(); i++) {
            CommandArgument argument = arguments.get(i);
            args.add(argument.forDisplay());
        }
        return args.toArray(new String[args.size()]);
    }


    public String toString() {
        return toString(getCommandLineForDisplay(), true);
    }

    /**
     * Converts the command line to a string without adding quotes to any of the arguments.
     */
    public String toStringForDisplay() {
        return toString(getCommandLineForDisplay(), false);
    }

    /**
     * Put quotes around the given String if necessary.
     * <p/>
     * <p>
     * If the argument doesn't include spaces or quotes, return it as is. If it contains double quotes, use single
     * quotes - else surround the argument by double quotes.
     * </p>
     *
     * @throws CommandLineException if the argument contains both, single and double quotes.
     */
    public static String quoteArgument(String argument) throws CommandLineException {
        if (argument.indexOf("\"") > -1) {
            if (argument.indexOf("\'") > -1) {
                throw new CommandLineException("Can't handle single and double quotes in same argument: " + argument);
            } else {
                return '\'' + argument + '\'';
            }
        } else if (argument.indexOf("\'") > -1 || argument.indexOf(" ") > -1) {
            return '\"' + argument + '\"';
        } else {
            return argument;
        }
    }

    public static String toString(String[] line, boolean quote) {
        return toString(line, quote, " ");
    }

    public static String toString(String[] line, boolean quote, String separator) {
        // empty path return empty string
        if (line == null || line.length == 0) {
            return "";
        }

        // path containing one or more elements
        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < line.length; i++) {
            if (i > 0) {
                result.append(separator);
            }
            if (quote) {
                try {
                    result.append(quoteArgument(line[i]));
                } catch (CommandLineException e) {
                    LOG.error("Error quoting argument.", e);
                }
            } else {
                result.append(line[i]);
            }
        }
        return result.toString();
    }

    public static String[] translateCommandLine(String toProcess) throws CommandLineException {
        if (toProcess == null || toProcess.length() == 0) {
            return new String[0];
        }

        // parse with a simple finite state machine

        final int normal = 0;
        final int inQuote = 1;
        final int inDoubleQuote = 2;
        int state = normal;
        StringTokenizer tok = new StringTokenizer(toProcess, "\"\' ", true);
        Vector v = new Vector();
        StringBuffer current = new StringBuffer();

        while (tok.hasMoreTokens()) {
            String nextTok = tok.nextToken();
            switch (state) {
                case inQuote:
                    if ("\'".equals(nextTok)) {
                        state = normal;
                    } else {
                        current.append(nextTok);
                    }
                    break;
                case inDoubleQuote:
                    if ("\"".equals(nextTok)) {
                        state = normal;
                    } else {
                        current.append(nextTok);
                    }
                    break;
                default:
                    if ("\'".equals(nextTok)) {
                        state = inQuote;
                    } else if ("\"".equals(nextTok)) {
                        state = inDoubleQuote;
                    } else if (" ".equals(nextTok)) {
                        if (current.length() != 0) {
                            v.addElement(current.toString());
                            current.setLength(0);
                        }
                    } else {
                        current.append(nextTok);
                    }
                    break;
            }
        }

        if (current.length() != 0) {
            v.addElement(current.toString());
        }

        if (state == inQuote || state == inDoubleQuote) {
            throw new CommandLineException("unbalanced quotes in " + toProcess);
        }

        String[] args = new String[v.size()];
        v.copyInto(args);
        return args;
    }

    public int size() {
        return getCommandLine().length;
    }


    /**
     * Sets execution directory.
     */
    public void setWorkingDirectory(String path) {
        if (path != null) {
            File dir = new File(path);
            checkWorkingDir(dir);
            workingDir = dir;
        } else {
            workingDir = null;
        }
    }

    /**
     * Sets execution directory
     */
    public void setWorkingDir(File workingDir) {
        checkWorkingDir(workingDir);
        this.workingDir = workingDir;
    }

    // throws an exception if the specified working directory is non null
    // and not a valid working directory
    private void checkWorkingDir(File dir) {
        if (dir != null) {
            if (!dir.exists()) {
                throw new CommandLineException("Working directory \"" + dir.getAbsolutePath() + "\" does not exist!");
            } else if (!dir.isDirectory()) {
                throw new CommandLineException("Path \"" + dir.getAbsolutePath() + "\" does not specify a "
                        + "directory.");
            }
        }
    }

    public File getWorkingDirectory() {
        return workingDir;
    }

    public Console execute(ConsoleOutputStreamConsumer outputStreamConsumer, EnvironmentVariableContext environmentVariableContext) {
        SafeOutputConsumer safeConsumer = new SafeOutputConsumer(outputStreamConsumer);
        safeConsumer.addArguments(arguments);
        safeConsumer.addArguments(secrets);

        Console console = new Console(this, createProcess(environmentVariableContext, safeConsumer), safeConsumer, encoding);
        if (!useKillableProcess()) {
            for (String input : this.inputs) {
                console.type(input);
            }
            console.finishInput();
        }
        return console;
    }

    private boolean useKillableProcess() {
        return killableProcess()!=null;
    }

    private String killableProcess() {
        return System.getProperty(CRUISE_KILLABLE_PROCESS_PROPERTY);
    }

    private Process createProcess(EnvironmentVariableContext environmentVariableContext, ConsoleOutputStreamConsumer consumer) {
        final String msgCommandInfo = "Executing: " + toString(getCommandLineForDisplay(), true);

        ProcessBuilder processBuilder = new ProcessBuilder(getCommandLine());
        LOG.debug(msgCommandInfo);
        if (workingDir != null) {
            LOG.debug("Using working directory " + workingDir.getAbsolutePath());
            processBuilder.directory(workingDir);
        }

        setEnvironmentVariables(processBuilder, environmentVariableContext, consumer);
        processBuilder.environment().putAll(env);

        Process process;
        try {
            LOG.debug("START command " + msgCommandInfo);
            process = processBuilder.start();
            LOG.debug("END command " + msgCommandInfo);
        } catch (IOException e) {
            throw new CommandLineException(
                    "Error happens when " + msgCommandInfo +
                            "\n Make sure this command can execute manually.", e);
        }
        return process;
    }

    public static void setEnvironmentVariables(ProcessBuilder pb, EnvironmentVariableContext environmentVariableContext,
                                               ConsoleOutputStreamConsumer consumer) {
        Map<String, String> env = pb.environment();

        environmentVariableContext.setupRuntimeEnvironment(env, consumer);
    }

    public String getExecutable() {
        return executable;
    }

    public CommandLine withArg(String argument) {
        this.arguments.add(new StringArgument(argument));
        return this;
    }

    public CommandLine withArgs(String... args) {
        addStringArguments(args);
        return this;
    }

    public CommandLine argPassword(String password) {
        arguments.add(new PasswordArgument(password));
        return this;
    }

    public CommandLine withWorkingDir(File folder) {
        setWorkingDir(folder);
        return this;
    }

    public static CommandLine createCommandLine(String command) {
        return new CommandLine(command);
    }

    public CommandLine withEnv(Map<String, String> env) {
        this.env.putAll(env);
        return this;
    }

    public CommandLine withArg(CommandArgument argument) {
        arguments.add(argument);
        return this;
    }

    public List<CommandArgument> getArguments() {
        return arguments;
    }

    public CommandLine withSecret(CommandArgument argument) {
        secrets.add(argument);
        return this;
    }

    public void addInput(String[] input) {
        inputs.addAll(Arrays.asList(input));
    }

    public CommandLine withEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public static class AsyncKiller extends Thread {
        private final Process p;
        private final long timeout;
        private boolean killed;

        AsyncKiller(final Process p, final long timeout) {
            this.p = p;
            this.timeout = timeout;
        }

        public void run() {
            try {
                sleep(timeout * 1000L);
                synchronized (this) {
                    p.destroy();
                    killed = true;
                }
            } catch (InterruptedException expected) {
            }
        }

        public synchronized boolean processKilled() {
            return killed;
        }
    }
}
