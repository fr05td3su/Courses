mkdir /home/box/web/public -p
mkdir /home/box/web/public/img
mkdir /home/box/web/public/css
mkdir /home/box/web/public/js

mkdir /home/box/web/uploads
mkdir /home/box/web/etc
cp etc/nginx.conf /etc/nginx/nginx.conf
#cp hello.py /home/box/web/hello.py

sudo apt-get install python-pip

sudo ln -sf /home/box/web/etc/nginx.conf /etc/nginx/sites-enabled/default
sudo ln -sf /home/box/etc/hello.py /etc/gunicorn.d/hello.py

gunicorn -w 4 -b 0.0.0.0:8080 hello:web_application

sudo /etc/init.d/nginx restart
