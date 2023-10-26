#!/bin/bash
# Run background
/run.sh

# Run script forever
while true;
        do echo "still live"; 
        sleep 5;
done

exec "$@"