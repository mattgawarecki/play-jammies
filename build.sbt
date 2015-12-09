name := "play-jsonapi-errorhandler"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play_2.11"               % "2.4.4",

  "org.powermock"     % "powermock-module-junit4" % "1.6.3" % Test,
  "org.powermock"     % "powermock-api-mockito"   % "1.6.3" % Test,
  "com.typesafe.play" % "play-test_2.11"          % "2.4.4" % Test,
  "org.assertj"       % "assertj-core"            % "3.2.0" % Test
)
