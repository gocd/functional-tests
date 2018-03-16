namespace :build do
  task :clean do
    cd "../#{GO_TRUNK_DIRNAME}" do
      sh "./gradlew clean"
    end
    cd "../#{GO_PLUGINS_DIRNAME}" do
      sh "mvn clean -q"
    end
  end

  task :version do
    require 'json'
    cd "../#{GO_TRUNK_DIRNAME}" do
      sh './gradlew -q installers:versionFile'
    end
    if ENV['GO_VERSION'].nil?
      ENV['GO_VERSION'] = JSON.parse(File.read("../#{GO_TRUNK_DIRNAME}/installers/target/distributions/meta/version.json"))['go_version']
    end
  end

  task :server do
    cd "../#{GO_TRUNK_DIRNAME}" do
      sh "./gradlew -q serverGenericZip"
    end
  end

  task :agent do
    cd "../#{GO_TRUNK_DIRNAME}" do
      sh "./gradlew -q agentGenericZip"
    end
  end

  task :api do
    cd "../#{GO_TRUNK_DIRNAME}" do
      sh "./gradlew -q :plugin-infra:go-plugin-api:install :plugin-infra:go-plugin-api-internal:install"
    end
  end

  task :plugins => [:api, :version] do
    cd "../#{GO_PLUGINS_DIRNAME}" do
      sh "mvn --quiet --batch-mode package -Dgo.version=$(jq '.go_full_version' -r ../#{GO_TRUNK_DIRNAME}/installers/target/distributions/meta/version.json) -DskipTests"
    end
  end

  task :addon do
    cd "../#{GO_TRUNK_DIRNAME}" do
      sh "./gradlew -q test-addon:assemble"
    end
  end

  task :all => [:clean, :server, :agent, :api, :addon, :plugins] 
end
