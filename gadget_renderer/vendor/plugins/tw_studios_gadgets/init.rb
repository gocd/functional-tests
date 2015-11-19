if RUBY_PLATFORM =~ /java/i

require 'java'

com.thoughtworks.studios.platform.SSLSelfSignedCertificateEnabler.enable
require 'shindig'

#for thread safe situations
Dir[File.expand_path("../app/**/*.rb", __FILE__)].each {|file| require file } if RAILS_ENV == "production"
end