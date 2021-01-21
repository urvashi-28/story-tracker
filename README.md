Docker Commands:

docker build -t story-tracker .
docker run -m512M --cpus 2 -it -p 8080:8080 --rm story-tracker
docker run -it -p 8080:8080 story-tracker
