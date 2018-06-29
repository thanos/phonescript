import os, glob,sys
path = os.path.join(sys.argv[1], "*.class")
simplify = lambda x: os.path.split(x)[-1].split('.')[0]
data = [(simplify(name), os.stat(name).st_size) for name in glob.glob(path)]
total = sum(map(lambda x: x[1],  data))
data.append(("Total", total))
data.sort()
print '||'+'||'.join([x[0] for x in data])+'||'
print '||'+'||'.join([str(x[1]) for x in data])+'||'






