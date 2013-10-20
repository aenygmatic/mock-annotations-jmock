mock-annotations-jmock
======================

Support library for jMock framework. It offers a Mockito style annotation usage for jMock
<br><br>

@Mock
  - Annotated objects will be created via ``Mockery`` annotated with ```@JMockery```

@JMockery
  - Fields annotated with ```@JMockery``` will serve as context for mock objects
  - Multiple fields can be annotated with ```@JMockery``` if so the field name can be refered within ```@Mock``` annotation to link the mock object to the ```Mockery```

@Injected
  - Mocked objects will be injected into the field annotated with @Injected
  - If the class has default constructor the field will be automaticaly instantiated
  - If the class has no default constructor mock objects will be used as constructor parameters
  - If the field is already instantiated only injection will be performed

Injection strategy:
  - Closest in inheritance tree
  - Matching generic parameters 
  - Matching name
  - Final and static field are skipped


```java
public class TestedObjectTest {

    @JMockery
    private Mockery context;
    
    @Mock
    private Component component;
    @Mock
    private UtilityClass utils;
    
    @Injected
    private TestedObject underTest;

    @Before
    public void setUp() {
        JMockAnnotations.initialize(this);
    }
    
    ...
}
```

Multiple ``Mockery`` contexts can be used for creating and grouping mocks.

```java
public class TestedObjectTest {

    @JMockery
    private Mockery sessionContext;
    @JMockery
    private Mockery serviceContext;
    
    @Mock(mockery = "sessionContext")
    private Component component;
    @Mock(mockery = "serviceContext")
    private UtilityClass utils;
    
    @Injected
    private TestedObject underTest;

    @Before
    public void setUp() {
        JMockAnnotations.initialize(this);
    }
    
    ...
}
```
