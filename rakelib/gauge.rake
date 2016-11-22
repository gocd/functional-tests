namespace :gauge do
  task :spec, [:name] => 'build:version' do |t, args|
    sh "GO_VERSION=#{ENV['GO_VERSION']} gauge specs/#{args[:name]}.spec}"
  end
end
