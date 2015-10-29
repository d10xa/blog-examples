# Удаляем директорию build
rm -rf build

# Создаем структуру war архива в build/tmp
mkdir -p build/tmp build/tmp/WEB-INF/ build/tmp/WEB-INF/classes/templates build/tmp/WEB-INF/lib build/tmp/WEB-INF/lib-provided

# Утилитой spring-boot-cli создаем jar
spring jar build/app.jar App.groovy ServletInitializer.groovy

# Распаковываем jar
unzip build/app.jar -d build/extracted_jar

# Библиотеки для встроенного сервера закидываем в папку lib-provided, остальные в lib
cp -p $(find build/extracted_jar/lib -name '*tomcat*') build/tmp/WEB-INF/lib-provided
cp -p $(find build/extracted_jar/lib -not -name '*tomcat*') build/tmp/WEB-INF/lib

# Копируем META-INF и спринговые классы в корень будующего war
cp -r build/extracted_jar/META-INF/ build/extracted_jar/org/ build/tmp/

# В манифесте меняем Main-Class JarLauncher на WarLauncher
sed -i -- 's/JarLauncher/WarLauncher/g' build/tmp/META-INF/MANIFEST.MF

# Копируем классы из пакета ru в WEB-INF/classes
cp -r build/extracted_jar/ru/ build/tmp/WEB-INF/classes

# Архивируем без сжатия, и размещаем war рядом с jar
cd build/tmp
zip -r --compression-method=store app.war *
mv app.war ../