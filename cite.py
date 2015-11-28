#!/usr/bin/env python3

# Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

import glob
import os

def find_lines_from_file(file):
    lines = list()
    try:
        with open(file, "r") as infile:
            data = [line.strip() for line in infile]
            for line in data:
                if line[:2] == "//":
                    line = line[2:]
                elif (line[:4] == "<!--") and (line[-3:] == "-->"):
                    line = line[4:-3]
                else:
                    continue
                if (1 <= line.count(";") <= 2) and (len(line[line.rfind(";") + 1:].strip()) == 10):
                        lines.append(line)
    except UnicodeDecodeError:
        pass
    return lines

def find_lines_from_path(path):
    lines = list()
    for item in glob.glob("{}/*".format(path)):
        if os.path.isdir(item):
            for line in find_lines_from_path(item):
                lines.append(line)
        elif os.path.isfile(item):
            for line in find_lines_from_file(item):
                lines.append(line)
    return lines

def main():
    lines = list()
    print("<table>")
    print("\t<tr>")
    for item in ["Name", "URL", "Date Retrieved"]:
        print("\t\t<td nowrap=\"nowrap\">{}</td>".format(item))
    print("\t</tr>")
    for line in sorted(list(set(find_lines_from_path("{}/app/src".format(os.getcwd()))))):
        line = line.strip().split(";")
        if len(line) == 2:
            line = ["N/A", line[0], line[1]]
        print("\t<tr>")
        for item in line:
            print("\t\t<td nowrap=\"nowrap\">{}</td>".format(item.strip()))
        print("\t</tr>")
    print("</table>")

if __name__ == "__main__":
    main()
