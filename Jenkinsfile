pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /Users/sylvain/.m2:/root/.m2'
        }
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                withSonarQubeEnv(installationName: 'ngrok syco') {
                   sh 'mvn -X -B -DskipTests clean package sonar:sonar'
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