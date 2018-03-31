cd /home/box/web/etc

sudo gunicorn --daemon --reload -b 0.0.0.0:8080 hello:web_application

cd /home/box/web/ask

sudo gunicorn --daemon --reload -b 0.0.0.0:8000 ask.wsgi

sudo /etc/init.d/nginx restart
