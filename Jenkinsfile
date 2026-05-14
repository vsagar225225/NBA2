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
          fileIncludePattern: '**/target/cucumber-report.json',
          sortingMethod: 'ALPHABETICAL'
      }
    }
  }

  post {
    always {
      // If you are not generating Surefire XML, keep this but allow empty results.
      junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
      archiveArtifacts artifacts: '**/target/**', fingerprint: true
    }
  }
}
