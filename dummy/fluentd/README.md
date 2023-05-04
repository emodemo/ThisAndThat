## FLUENTD LOCAL TESTING:

* create a file `fluentd.conf` with the parsing format to test
* create another folder where the log to be parsed and flunetd tracking file will reside
* run `docker run -v <LOG_FILES_FOLDER>:/var/log -v <fluentd.conf FOLDER>:/fluentd/etc fluent/fluentd:edge-debian -c /fluentd/etc/fluentd.conf`
* copy files containing lines to be parsed into `<LOG_FILES_FOLDER>` and see the console out of the container for a result.
* to repeat delete the `<LOG_FILES_FOLDER>/fluentd-backend_stdout.log.pos` and restart the container to reload `fluentd.conf`

docker run -v varlog:/var/log -v etc:/fluentd/etc emo/fluentd:latest -c /fluentd/etc/fluentd.conf
docker run -v C:\works_emo\ThisAndThat\dummy\fluentd\varlog:/var/log -v C:\works_emo\ThisAndThat\dummy\fluentd\etc:/fluentd/etc emo/fluentd:latest -c /fluentd/etc/fluentd.conf
