#!groovy

pipeline {
    agent { docker 'maven:3.3.9' }
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
            archive 'build/libs/**/*.jar'
            junit 'build/reports/**/*.xml'
        }
        success {
        	 mail to: 'sudeepk@ccs.neu.edu',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
}
