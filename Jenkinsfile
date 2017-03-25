#!groovy

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
            	slackSend (color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Quality') {
        	sh 'mvn site'
        }
    }
    post {
        always {
            archive 'target/**/*.jar'
            step([$class: 'CheckStylePublisher', pattern: 'target/checkstyle-result.xml'])
            junit 'target/surefire-reports/**/*.xml'
        }
        success {
        	slackSend (color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        failure {
        	slackSend (color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}