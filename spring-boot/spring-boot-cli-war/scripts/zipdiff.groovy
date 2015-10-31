#!/usr/bin/env groovy
import java.util.zip.ZipFile

ZipFile.metaClass.fileNames {
    delegate.entries()
            .collect { it }
            .findAll { !it.isDirectory() }
            .collect { it.name }
            .sort()
}
List<String> list1 = new ZipFile(args[0]).fileNames()
List<String> list2 = new ZipFile(args[1]).fileNames()

println "---unique in ${args[0]}"
list1.findAll { !list2.contains(it) }.each { println it }

println "---unique in ${args[1]}"
list2.findAll { !list1.contains(it) }.each { println it }