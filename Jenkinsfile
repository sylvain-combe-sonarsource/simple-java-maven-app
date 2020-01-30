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
                   sh 'mvn -X -B -Dsonar.branch.name=$GIT_BRANCH -DskipTests clean package sonar:sonar'
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