cd ../..
python cheeser.py -b FrogieHub
python cheeser.py -b FrogieCheese
python cheeser.py -b CheeseDocumentation
python cheeser.py -b FrogieCloudos

set message=Remove log

cd projects/FrogieHub
git add *
git commit -m "%message%"
git push
cd ../FrogieCheese
git add *
git commit -m "%message%"
git push
cd ../CheeseDocumentation
git add *
git commit -m "%message%"
git push
cd ../FrogieCloudos
git add *
git commit -m "%message%"
git push