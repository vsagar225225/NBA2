pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
				git branch: 'main', url: 'https://github.com/vsagar225225/NBA2.git'
            }
        }

        stage('Build & Test') {
            steps {
				bat 'mvn clean test'
            }
        }

        stage('Publish Cucumber Report') {
            steps {
                cucumber buildStatus: 'UNSTABLE',
                fileIncludePattern: '**/target/cucumber.json',
                sortingMethod: 'ALPHABETICAL'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/**', fingerprint: true
        }
    }
}