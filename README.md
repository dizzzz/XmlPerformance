# XmlPerformance
A small project to measure performance of XML tooling (processors)

Used StAX Parsers:
- https://github.com/FasterXML/woodstox
- https://github.com/FasterXML/aalto-xml
- Oracle Java Bundled reference implementation

## Get started:
* add a file `in.xml` and `in.xsd` into the `data` directory (yet missing)
* run `nl.wexsol.xml.performance.parse.stax.Main` or `nl.wexsol.xml.performance.compression.Main`


## Dataset
* 6.8 Mb XML
* 903 kb XSD
* 100 iterations

## Results
### Compression
```
Original size: 6 MB
Repetitions: 10

Compressed sizes:
gzip=546 KB
xz=243 KB
bzip2=275 KB
exi=235 KB
exi-schema=198 KB
fastinfoset=1 MB

Timing: 
gzip: avg=142.4 ms  max=150 ms  min=139 ms
xz: avg=2859.0 ms  max=3017 ms  min=2660 ms
bzip2: avg=1743.1 ms  max=2027 ms  min=1664 ms
exi: avg=967.4 ms  max=987 ms  min=954 ms
exi-schema: avg=965.8 ms  max=1045 ms  min=924 ms
fastinfoset: avg=160.0 ms  max=244 ms  min=147 ms
```

Conclusions (for this dataset!)
- fastinfoset compression yields in a larger file size compared to the generic GZIP compressor
- EXI compression is 5-6 times slower compared to GZIP compression
- EXI compression is yields in files 1/2 the size compared to GZIP
- XZ and Bzip2 compress better than GZip but are resp. 20 and 10 times slower.

### StAX
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

Conclusions (for this dataset!)
- In general, StreamReaders are upto 2 times faster compared to EventReaders
- The "Aalto XML processor" is the fastest StAX parser followed by the WoodStox parser. The Oracle VM provides the slowest StAX parser.
