package ru.d10xa.springwar;

@RestController
@RequestMapping('/')
class Ctrl{
    @RequestMapping
    def map(){
        return ['a':'b']
    }
}