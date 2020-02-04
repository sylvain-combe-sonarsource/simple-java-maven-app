if (env.BRANCH_NAME == 'master')
   mybranch = ''
else
  mybranch = env.BRANCH_NAME

node {
  echo "$env"
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
                     sh 'mvn -X -B -Dsonar.branch.name=${mybranch} -DskipTests clean package sonar:sonar'
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