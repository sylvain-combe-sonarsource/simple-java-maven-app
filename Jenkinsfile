if (env.BRANCH_NAME == 'master')
  myBranch = ''
else
  myBranch = env.BRANCH_NAME

node {
  echo "Jenkins variables"
  echo env.BRANCH_NAME
  echo myBranch
  echo env.CHANGE_ID
  echo env.CHANGE_BRANCH
  echo env.CHANGE_TARGET
}

if(env.CHANGE_ID != null) // PR analysis
  mvnCmdLine = "mvn -X -B -DskipTests \
                   -Dsonar.pullrequest.key=${env.CHANGE_ID} \
                   -Dsonar.pullrequest.branch=${myBranch} \
                   -Dsonar.pullrequest.base=${env.CHANGE_TARGET} \
                   clean package sonar:sonar"
else // regular branch analysis
  mvnCmdLine = "mvn -X -B -DskipTests \
                   -Dsonar.branch.name=${myBranch} \
                   clean package sonar:sonar"

node {
  echo "My command line:"
  echo mvnCmdLine
}

pipeline {
    agent {
        docker {
            // image 'masstroy/alpine-docker-java-maven'
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('SCM') {
            steps {
                // git 'https://github.com/sylvain-combe-sonarsource/simple-java-maven-app.git'
              // sh "git fetch --no-tags ${GIT_URL} +refs/heads/master:refs/remotes/origin/master"
              sh "echo ${GIT_URL}"
            }
        }
        stage('Build') {
            steps {
                withSonarQubeEnv(installationName: 'SQ82') {
                 //    script {
                         // fetch master from origin so sonar scanner comparison works
                         // sh "git fetch --no-tags ${GIT_URL} +refs/heads/master:refs/remotes/origin/master"
                    sh "${mvnCmdLine}"
                    // sh 'mvn clean package sonar:sonar'
                //    }
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
}
