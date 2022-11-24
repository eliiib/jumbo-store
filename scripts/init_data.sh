#!/bin/bash

mongoimport --host mongodb --db jumbo --collection stores --type json --file stores_transformed.json --jsonArray
