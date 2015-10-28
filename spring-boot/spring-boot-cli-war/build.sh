rm -rf build
mkdir -p build/tmp/WEB-INF/classes/templates
mkdir build/tmp/WEB-INF/lib
mkdir build/tmp/WEB-INF/lib-provided
spring jar build/app.jar App.groovy ServletInitializer.groovy
unzip build/app.jar -d build/extracted_jar
cp -p $(find build/extracted_jar/lib -name '*tomcat*') build/tmp/WEB-INF/lib-provided
cp -p $(find build/extracted_jar/lib -not -name '*tomcat*') build/tmp/WEB-INF/lib
cp -r build/extracted_jar/META-INF/ build/extracted_jar/org/ build/tmp/
sed -i -- 's/JarLauncher/WarLauncher/g' build/tmp/META-INF/MANIFEST.MF
cp -r build/extracted_jar/ru/ build/tmp/WEB-INF/classes
cd build/tmp
zip -r --compression-method=store app.war *
mv app.war ../
