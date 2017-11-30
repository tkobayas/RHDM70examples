# retrieve timers

curl -X GET -u 'bpmsAdmin:password1!' http://localhost:8080/kie-execution-server/services/rest/server/admin/containers/org.kie.example:project1:1.0.0-SNAPSHOT/processes/instances/4/timers/

# update timer

curl -X GET -u 'bpmsAdmin:password1!' http://localhost:8080/kie-execution-server/services/rest/server/admin/containers/org.kie.example:project1:1.0.0-SNAPSHOT/processes/instances/4/timers/1
