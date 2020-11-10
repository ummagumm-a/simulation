#!/bin/bash
clear
find ./* -iname "*.java" > sources.txt
javac @sources.txt
java ./simulator/viacheslav_sinii/Simulator.java
