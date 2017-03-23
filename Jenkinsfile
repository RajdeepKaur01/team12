#!groovy

pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
    post {
        always {
            archive 'target/**/*.jar'
            junit 'target/surefire-reports/**/*.xml'
        }
        success {
        	 mail to: 'sudeepk@ccs.neu.edu',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
}