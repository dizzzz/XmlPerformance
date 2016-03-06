# XmlPerformance
A small project to measure performance of XML tooling (processors)


## Get started:
* add a file 'in.xml' and 'in.xsd' into the `data` directory (yet missing)
* run `nl.wexsol.xml.performance.parse.stax.Main` or `nl.wexsol.xml.performance.compression.Main`

# Results

Details 
* 6.8 Mb XML
* 903 kb XSD
* 100 iterations

## Compress
```
Original size: 6 MB
Repetitions: 10

Compressed sizes:
gzip=546 KB
exi=235 KB
exi-schema=198 KB
fastinfoset=1 MB

Timing: 
gzip: avg=146.5 ms  max=196 ms  min=139 ms
exi: avg=972.3 ms  max=1057 ms  min=930 ms
exi-schema: avg=931.5 ms  max=1206 ms  min=883 ms
fastinfoset: avg=151.3 ms  max=192 ms  min=143 ms
```

## StAX
```
Original size: 6 MB
Repetitions: 100

com.sun.xml.internal.stream.XMLInputFactoryImpl
EventReader: #=316854  avg=225.55 ms  max=548 ms  min=210 ms
StreamReader: #=316854  avg=107.33 ms  max=121 ms  min=104 ms

com.fasterxml.aalto.stax.InputFactoryImpl
EventReader: #=316854  avg=118.54 ms  max=346 ms  min=108 ms
StreamReader: #=316854  avg=70.05 ms  max=96 ms  min=66 ms

com.ctc.wstx.stax.WstxInputFactory
EventReader: #=316854  avg=145.99 ms  max=316 ms  min=137 ms
StreamReader: #=316854  avg=88.43 ms  max=97 ms  min=87 ms
```
