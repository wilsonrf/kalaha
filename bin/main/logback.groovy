import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import static ch.qos.logback.classic.Level.*

def LOG_PATH = System.getProperty("log.dir") ?: "log"

def ACTIVE_PROFILE = System.getProperty("spring.profiles.active") ?: "default"

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

appender("FILE", RollingFileAppender) {
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy){
        fileNamePattern = "${LOG_PATH}/currency-converter.%d{yyyy-MM-dd}.log"
        maxHistory = 10
    }
}

def appenderList = ['STDOUT', 'FILE']


logger('org.mongodb', WARN, appenderList, false)
logger('org.springframework.data.mongodb', TRACE, appenderList, false)
logger('org.springframework.security', INFO, appenderList, false)
logger('org.apache.http', TRACE, appenderList, false)
logger('org.thymeleaf', INFO, appenderList, false)
logger('com.wilsonfranca', DEBUG, appenderList, false)
root(INFO, appenderList)
