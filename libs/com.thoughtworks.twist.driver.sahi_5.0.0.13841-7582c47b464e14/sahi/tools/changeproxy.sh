networkserviceorder=$1
networksetup -getwebproxy $networkserviceorder > proxyinfo.txt
if [ `(awk '/Enabled:/ {print $2;exit}' proxyinfo.txt)` == "No" ] || [ `(awk '/Server:/ {print $2}' proxyinfo.txt)` != "127.0.0.1" ] || [ `(awk '/Port:/ {print $2}' proxyinfo.txt)` != "9999" ]
then
networksetup -setwebproxy $networkserviceorder 127.0.0.1 9999
fi

networksetup -getsecurewebproxy $networkserviceorder > proxyinfossl.txt
if [ `(awk '/Enabled:/ {print $2;exit}' proxyinfossl.txt)` == "No" ] || [ `(awk '/Server:/ {print $2}' proxyinfossl.txt)` != "127.0.0.1" ] || [ `(awk '/Port:/ {print $2}' proxyinfossl.txt)` != "9999" ]
then
networksetup -setsecurewebproxy $networkserviceorder 127.0.0.1 9999
fi
