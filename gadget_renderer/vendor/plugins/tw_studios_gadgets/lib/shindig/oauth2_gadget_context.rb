module Shindig
  class Oauth2GadgetContext < GadgetContext
    def initialize(security_token)
      super()
      @st = security_token
    end
    
    def getContainer
      @st.container
    end
    
    def getToken
      @st
    end
    
    def getUrl
      Uri.parse(@st.app_url)
    end
    
    def getIgnoreCache
      return true
    end
  end
end