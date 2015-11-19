task :fork do
  COUNTER = (ENV["COUNTER"] || 10).to_i
  puts "Current counter %s" % COUNTER
  if COUNTER == 0
    sleep(60*5)
    puts "Process was not killed, it returned."
  else
    system("rake -f #{__FILE__} fork COUNTER=#{COUNTER - 1}")
  end
end

