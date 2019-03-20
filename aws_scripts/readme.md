# AWS

## get docker image locally

```sh
# will ask for credentials and region
aws configure
# will give docker-login - use it
aws ecr get-login --no-include-email
# full string from previous command
docker login ...
# describes the images
aws ecr describe-repositories
aws ecr describe-images --repository-name <image_name>
# see the URI of the repository
docker pull repositoryUri:tag
docker pull <aws_account_id>.dkr.ecr.<region>.amazonaws.com/<image_name>:<tag>
docker run -it -v C:\workspaces\ws1:/home/workspaces/ws1 f33e9a6c90c9
```

## notes

* `pipeline.yml` to create a pipeline with codecommit, 2 codebuilds, and lambda
* `pipeline_junit.yml` to update the pipeline by adding another codebuild. Better to do this with change-set than directly updating the pipeline. If another change set is created with the original `pipeline.yml`, the newly created junit will be removed from the pipeline and deleted.

## CLI examples

```sh
$ aws cloudformation create-stack --stack-name emo74stack --capabilities CAPABILITY_IAM --template-body file://pipeline.yml --parameters ParameterKey=repositoryName,ParameterValue=emorepo01 ParameterKey=dockerImageName,ParameterValue=microfocus/docker/repo/vcdevhub:5.0

$ aws cloudformation create-stack --stack-name emo01user --capabilities CAPABILITY_NAMED_IAM --template-body file://create_user.yml --parameters ParameterKey=userName,ParameterValue=go6o@to6o.com

$ aws cloudformation update-stack --stack-name emo01user --capabilities CAPABILITY_NAMED_IAM --template-body file://create_user_test.yml --parameters ParameterKey=userName,ParameterValue=go6o@to6o.com

$ aws cloudformation create-change-set --stack-name emo01user --change-set-name updatego6o2 --capabilities CAPABILITY_NAMED_IAM --template-body file://create_user.yml --parameters ParameterKey=userName,ParameterValue=go6o@to6o.com

$ aws cloudformation execute-change-set --stack-name emo01user --change-set-name updatego6o2
```