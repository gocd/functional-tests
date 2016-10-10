# -*- encoding: utf-8 -*-
# stub: jruby-openssl 0.9.17 java lib

Gem::Specification.new do |s|
  s.name = "jruby-openssl"
  s.version = "0.9.17"
  s.platform = "java"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Ola Bini", "JRuby contributors"]
  s.date = "2016-06-09"
  s.description = "JRuby-OpenSSL is an add-on gem for JRuby that emulates the Ruby OpenSSL native library."
  s.email = "ola.bini@gmail.com"
  s.homepage = "https://github.com/jruby/jruby-openssl"
  s.licenses = ["EPL-1.0", "GPL-2.0", "LGPL-2.1"]
  s.require_paths = ["lib"]
  s.requirements = ["jar org.bouncycastle:bcpkix-jdk15on, 1.54", "jar org.bouncycastle:bcprov-jdk15on, 1.54"]
  s.rubygems_version = "2.1.9"
  s.summary = "JRuby OpenSSL"

  if s.respond_to? :specification_version then
    s.specification_version = 4

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_development_dependency(%q<jar-dependencies>, ["~> 0.1"])
      s.add_development_dependency(%q<mocha>, ["~> 1.1.0"])
      s.add_development_dependency(%q<ruby-maven>, ["~> 3.0"])
    else
      s.add_dependency(%q<jar-dependencies>, ["~> 0.1"])
      s.add_dependency(%q<mocha>, ["~> 1.1.0"])
      s.add_dependency(%q<ruby-maven>, ["~> 3.0"])
    end
  else
    s.add_dependency(%q<jar-dependencies>, ["~> 0.1"])
    s.add_dependency(%q<mocha>, ["~> 1.1.0"])
    s.add_dependency(%q<ruby-maven>, ["~> 3.0"])
  end
end
