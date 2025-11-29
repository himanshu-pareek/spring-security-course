#!/bin/bash

curl localhost:8080/documents \
  -X POST \
  -u "$1" \
  -H 'Content-Type: application/json' \
  -d '[ { "id": 1, "owner": "java", "name": "Some title" }, { "id": 2, "owner": "rush", "name": "Some title" }, { "id": 3, "owner": "rush", "name": "Some title" } ]'
