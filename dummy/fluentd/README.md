## FLUENTD LOCAL TESTING:

* create a file `fluentd.conf` with the parsing format to test
* create another folder where the log to be parsed and flunetd tracking file will reside
* run `docker run -v <LOG_FILES_FOLDER>:/var/log -v <fluentd.conf FOLDER>:/fluentd/etc fluent/fluentd:edge-debian -c /fluentd/etc/fluentd.conf`
* copy files containing lines to be parsed into `<LOG_FILES_FOLDER>` and see the console out of the container for a result.
* to repeat delete the `<LOG_FILES_FOLDER>/fluentd-backend_stdout.log.pos` and restart the container to reload `fluentd.conf`
* see here for fluentd version used by splunk: https://github.com/splunk/fluent-plugin-splunk-hec/blob/1.2.1/docker/Gemfile

docker run -v varlog:/var/log -v etc:/fluentd/etc emo/fluentd:latest -c /fluentd/etc/fluentd.conf
docker run -v C:\works_emo\ThisAndThat\dummy\fluentd\varlog:/var/log -v C:\works_emo\ThisAndThat\dummy\fluentd\etc:/fluentd/etc emo/fluentd:latest -c /fluentd/etc/fluentd.conf
docker run --name my-fluentd -v /mnt/c/works_emo/ThisAndThat/dummy/fluentd/varlog:/var/log -v /mnt/c/works_emo/ThisAndThat/dummy/fluentd/etc:/fluentd/etc emo/fluentd:test1 -c /fluentd/etc/fluentd.conf
docker exec -it my-fluentd /bin/bash
# RUN FROM WINDOWS PATH AS /mnt/c/ is causing some rights issues
# for some reason the data.bq*.log file is with ??? user and rights, so check its content to see the parsed file
# even when running with root user there are some issues:
#  [output_file] unexpected error while checking flushed chunks. ignored. error_class=RuntimeError error="can't enqueue buffer file: path = /fluentd/etc/data.b600eb1105006059f5fece0433c3005b3.log, error = 'No such file or directory @ rb_sysopen - /fluentd/etc/data.q600eb1105006059f5fece0433c3005b3.log'"

docker run --rm --name my-fluentd -v $(pwd)/varlog:/var/log -v $(pwd)/etc:/fluentd/etc emo/fluentd:test1 -c /fluentd/etc/fluentd.conf