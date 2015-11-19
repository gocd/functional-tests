networkserviceorder=$1
networksetup -setwebproxy $networkserviceorder `(awk '/Server:/ {print $2}' proxyinfo.txt)` `(awk '/Port:/ {print $2}' proxyinfo.txt)`

if test `(awk '/Enabled:/ {print $2;exit}' proxyinfo.txt)` == "No"
then
networksetup -setwebproxystate $networkserviceorder off
fi

networksetup -setsecurewebproxy $networkserviceorder `(awk '/Server:/ {print $2}' proxyinfossl.txt)` `(awk '/Port:/ {print $2}' proxyinfossl.txt)`

if test `(awk '/Enabled:/ {print $2;exit}' proxyinfossl.txt)` == "No"
then
networksetup -setsecurewebproxystate $networkserviceorder off
fi
