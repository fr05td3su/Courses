cp -vr . /home/box/web/ 

cd /home/box/web

mkdir -p public/img
mkdir public/css
mkdir public/js
mkdir uploads

sudo rm /etc/nginx/sites-enabled/default
sudo rm /etc/nginx/sites-available/default

sudo cp --remove-destination etc/nginx.conf /etc/nginx/nginx.conf

sudo cp --remove-destination etc/hello.py /etc/gunicorn.d/hello.py
sudo cp --remove-destination ask/ask/wsgi.py /etc/gunicorn.d/wsgi.py

