name := "scala-china"

scalaVersion := "2.9.1"
//crossScalaVersions in ThisBuild := Seq("2.9.2", "2.9.1-1", "2.9.1")

crossScalaVersions := Seq("2.9.2","2.9.1")

// If using JRebel
//jettyScanDirs := Nil
scanDirectories in Compile := Nil

resolvers ++= Seq(
	"snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
	"releases" at "http://oss.sonatype.org/content/repositories/releases",
	"Media4u101 SNAPSHOT Repository" at "http://www.media4u101.se:8081/nexus/content/repositories/snapshots/",
	"Media4u101 Repository" at "http://www.media4u101.se:8081/nexus/content/repositories/releases/"   
) 

//seq(webSettings :_*)

seq(cloudBeesSettings :_*)

//seq(lessSettings:_*)

CloudBees.apiKey := Some("DCCBAA182C43FAA4")

CloudBees.apiSecret := Some("XUAVAOFUQSQGH5XPEUZPB+/SL4XAY0BI8/IP1E2Q4PA=")

CloudBees.username := Some("liujiuwu")

CloudBees.applicationId := Some("liujiuwu/scala-china")

seq(com.github.siasia.WebPlugin.webSettings :_*)

port in container.Configuration := 9000

scalacOptions ++= Seq("-deprecation", "-unchecked")

//EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

//EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE16)

//EclipseKeys.withSource := true

//(resourceManaged in (Compile, LessKeys.less)) <<= (crossTarget in Compile)(_ / "your_preference" / "css")

//下面2个 %表示会依赖特定的scala版本，1个 %可以自己设定scala版本或不指定

libraryDependencies ++= {
  val liftVersion = "2.5-M1"
  Seq(
    "net.liftweb"         %% "lift-webkit"            % liftVersion                          % "compile->default"  withSources(),
    "net.liftweb"         %% "lift-mapper"            % liftVersion                          % "compile->default"  withSources(),
    "net.liftmodules"     %% "lift-jquery-module"     % (liftVersion + "-1.0")	             % "compile->default",
    //"net.liftmodules"     %% "fobo"                   % ("2.5-SNAPSHOT" + "-0.7.1-SNAPSHOT") % "compile->default",
    "mysql" 	          %  "mysql-connector-java"   % "5.1.21",
    "org.eclipse.jetty"   % "jetty-webapp"            % "7.5.4.v20111024"                    % "container; test",
    "ch.qos.logback"      % "logback-classic"         % "1.0.6"
  )
}