This does not work if run from bash script -- need to copy and run from console (for now)

ssh \
-L 5601:127.0.0.1:5601 \
-L 9091:127.0.0.1:9091 \
-L 8888:127.0.0.1:8888 \
-L 8787:127.0.0.1:8787 \
-L 8080:127.0.0.1:8080 \
-L 4000:127.0.0.1:4000 \
-L 3000:127.0.0.1:3000 \
-L 5000:127.0.0.1:5000 \
-L 9200:127.0.0.1:9200 \
kabir@on-hetzner.host
