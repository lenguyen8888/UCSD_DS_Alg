import os, sys

if len(sys.argv) < 1:
    print("Syntax: {0} java_source_file".format(sys.argv[0]))
    exit(0)

javaSource = sys.argv[1]
output = os.popen("javac {0}".format(javaSource)).readlines()
javaClass = os.path.splitext(javaSource)[0]
print(output)
for fname in os.listdir("tests"):
    if fname.endswith(".a"):
        inputFile = os.path.join("tests", fname.replace(".a", ""))
        cmdOutput = os.popen("java {0} < {1}".format(javaClass, inputFile)).readlines()
        answerFile = os.path.join("tests", fname)
        with open(answerFile) as f:
            answer = f.readlines()
        if answer != None and cmdOutput == answer:
            print("testcase " + inputFile + " Passed")
        else:
            print("testcase " + inputFile + " Failed")
