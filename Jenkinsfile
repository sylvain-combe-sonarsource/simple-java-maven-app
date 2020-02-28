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

// mvnCmdLine2 = "mvn -X -B -DskipTests \
//                    -Dsonar.branch.name=${env.BRANCH_NAME} \
//                    clean package sonar:sonar"
// mvnCmdLine3 = "mvn -X -B -DskipTests \
//                    -Dsonar.branch.name=$myBranch \
//                    clean package sonar:sonar"

node {
  echo "My command line:"
  echo mvnCmdLine
}

pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
        }
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                 withSonarQubeEnv(installationName: 'ngrok syco') {
                     script {
                         // fetch master from origin so sonar scanner comparison works
                         sh "git fetch --no-tags ${GIT_URL} +refs/heads/master:refs/remotes/origin/master"
                         sh "${mvnCmdLine}"
                     }
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
