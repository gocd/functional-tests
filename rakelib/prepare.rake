namespace :prepare do
  task :clean do
    rm_rf 'target'
  end

  def version
    ENV['GO_VERSION']
  end

  task :plugins => ['build:plugins', 'build:version'] do
    mkdir_p "target/go-server-#{version}/plugins/external"
    cp_r "../#{GO_PLUGINS_DIRNAME}/target/go-plugins-dist/.", "target/go-server-#{version}/plugins/external"
  end

  task :server => 'build:server' do
    mkdir_p "target"
    sh "unzip ../#{GO_TRUNK_DIRNAME}/installers/target/distributions/zip/go-server-*.zip -d ./target"
  end

  task :agent => 'build:agent' do
    mkdir_p "target"
    sh "unzip ../#{GO_TRUNK_DIRNAME}/installers/target/distributions/zip/go-agent-*.zip -d ./target"
  end

  task :addon => ['build:addon', 'build:version'] do
    mkdir_p "target/test-addon"
    cp_r "../#{GO_TRUNK_DIRNAME}/test-addon/target/libs/.", "target/test-addon"
    cp_r "target/test-addon/.", "target/go-server-#{version}/addons"
  end

  task :properties do
    require 'socket'
    file = "src/test/java/twist.#{Socket.gethostname}.properties"
    if File.exists? file
      cp_r file, 'src/test/java/twist.properties'
    end
  end

  task :dependencies do
    sh 'mvn clean install -q'
  end

  task :all => ['prepare:clean', 'build:clean', :dependencies, :server, :agent, :plugins, :addon, :properties]
end

desc 'Prepare the server, agent and plugins for testing'
task :prepare => 'prepare:all'
