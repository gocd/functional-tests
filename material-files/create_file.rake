task :create do
  File.exist?("big_file.txt") && File.delete("big_file.txt")
  SIZE = (ENV['SIZE'] || 1).to_i * 1024
  SIZE.times do
    `cat kilo-file >> big_file.txt`
  end
end
