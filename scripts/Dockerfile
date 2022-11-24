FROM mongo:5.0

COPY stores_transformed.json .
COPY init_data.sh .
CMD ["bash", "init_data.sh"]