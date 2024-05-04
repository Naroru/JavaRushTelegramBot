#!/bin/bash

# Pull new changes
git pull

# Add environment variables to server
export BOT_NAME=$1
export BOT_TOKEN=$2
export BOT_DB_USER='prod_jrtb_db_user'
export BOT_DB_PASSWORD='Pap9L9VVUkNYj99GCUCC3mJkb'

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Start new deployment
docker-compose up --build -d