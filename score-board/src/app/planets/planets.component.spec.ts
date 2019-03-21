import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PlanetsComponent} from './planets.component';

describe('PlanetsComponent', () => {
  let component: PlanetsComponent;
  let fixture: ComponentFixture<PlanetsComponent>;

  let meshMocks = [];
  const createMeshMock = () => ({
    geometry: {
      scale: jasmine.createSpy()
    },
    position: {set: jasmine.createSpy()},
    rotateY: jasmine.createSpy(),
    rotateZ: jasmine.createSpy(),
    visible: true
  });

  let framesRequested = 0;
  const onlyOneFrame = animate => {
    if (framesRequested === 0) {
      framesRequested++;
      animate();
    }
  };

  beforeEach(async(() => {
    meshMocks = [];
    const sphereGeometryMock = {};
    TestBed
      .configureTestingModule({
        declarations: [PlanetsComponent],
        providers: [
          {
            provide: 'THREE', useValue: {
              WebGLRenderer: jasmine.createSpy().and.returnValue({
                setSize: jasmine.createSpy(),
                domElement: document.createElement('div')
              }),
              Scene: jasmine.createSpy().and.returnValue({add: jasmine.createSpy()}),
              LoadingManager: jasmine.createSpy().and.callFake((resolve) => resolve()),
              FontLoader: jasmine.createSpy().and.returnValue({load: jasmine.createSpy()}),
              TextureLoader: jasmine.createSpy().and.returnValue({load: jasmine.createSpy()}),
              Mesh: jasmine.createSpy().and.callFake((geometry) => {
                const mock = createMeshMock();
                if (geometry === sphereGeometryMock) {
                  meshMocks.push(mock);
                }
                return mock;
              }),
              MeshPhongMaterial: jasmine.createSpy(),
              SphereGeometry: jasmine.createSpy().and.returnValue(sphereGeometryMock),
              OrthographicCamera: jasmine.createSpy().and.returnValue({position: {set: jasmine.createSpy()}}),
              AmbientLight: jasmine.createSpy().and.returnValue({position: {set: jasmine.createSpy()}}),
              DirectionalLight: jasmine.createSpy().and.returnValue({position: {set: jasmine.createSpy()}}),
              TextGeometry: jasmine.createSpy().and.returnValue({center: jasmine.createSpy()}),
              MeshBasicMaterial: jasmine.createSpy(),
            }
          },
          {
            provide: 'requestAnimationFrame', useValue: onlyOneFrame
          }
        ]
      })
      .compileComponents()
      .then(() => {
        fixture = TestBed.createComponent(PlanetsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(meshMocks.length).toBe(25);
  });

  it('should hide all planets by default', () => {
    const actuals = meshMocks.map(meshMock => meshMock.visible);
    const expected = meshMocks.map(() => false);
    expect(actuals).toEqual(expected);
  });

  it('should show planets with a score', () => {
    const planetIndex = 2;
    component.playerScores = [
      {
        playerId: component['planets'][planetIndex].name,
        score: 1,
        progression: 1,
        devPoint: 1,
        secPoint: 1,
        opsPoint: 1,
      }
    ];
    fixture.detectChanges();
    expect(meshMocks[planetIndex].visible).toBe(true);
  });

  it('should scale planets when first scores received', () => {
    const planet1Index = 1;
    const planet2Index = 3;
    component.playerScores = [
      {
        playerId: component['planets'][planet1Index].name,
        score: 1,
        progression: 1,
        devPoint: 1,
        secPoint: 1,
        opsPoint: 1,
      },
      {
        playerId: component['planets'][planet2Index].name,
        score: 0,
        progression: 0,
        devPoint: 1,
        secPoint: 0,
        opsPoint: 1,
      }
    ];
    fixture.detectChanges();

    const planet1Scale = 1;
    const planet2Scale = 0.3;
    const expectedCalls = meshMocks.map((meshMock, index) => {
      if (index === planet1Index || index === planet2Index) {
        return 1;
      }
      return 0;
    });
    const actualCalls = meshMocks.map(meshMock => meshMock.geometry.scale.calls.count());
    expect(actualCalls).toEqual(expectedCalls);
    expect(meshMocks[planet1Index].geometry.scale).toHaveBeenCalledWith(planet1Scale, planet1Scale, planet1Scale);
    expect(meshMocks[planet2Index].geometry.scale).toHaveBeenCalledWith(planet2Scale, planet2Scale, planet2Scale);
  });

  it('should scale planets every time scores change', () => {
    const planet1Index = 1;
    const planet2Index = 3;
    component.playerScores = [
      {
        playerId: component['planets'][planet1Index].name,
        score: 1,
        progression: 1,
        devPoint: 1,
        secPoint: 1,
        opsPoint: 1,
      },
      {
        playerId: component['planets'][planet2Index].name,
        score: 0,
        progression: 0,
        devPoint: 1,
        secPoint: 0,
        opsPoint: 1,
      }
    ];
    fixture.detectChanges();
    for (const meshMock of meshMocks) {
      meshMock.geometry.scale.calls.reset();
    }

    component.playerScores = [
      {
        playerId: component['planets'][planet1Index].name,
        score: 1,
        progression: 1,
        devPoint: 1,
        secPoint: 1,
        opsPoint: 1,
      },
      {
        playerId: component['planets'][planet2Index].name,
        score: 2,
        progression: 2,
        devPoint: 2,
        secPoint: 2,
        opsPoint: 2,
      }
    ];
    fixture.detectChanges();
    const planet1Scale = 0.09 / 0.3;
    const planet2Scale = 0.3 / 0.09;
    const expectedCalls = meshMocks.map((meshMock, index) => {
      if (index === planet1Index || index === planet2Index) {
        return 1;
      }
      return 0;
    });
    const actualCalls = meshMocks.map(meshMock => meshMock.geometry.scale.calls.count());
    expect(actualCalls).toEqual(expectedCalls);
    expect(meshMocks[planet1Index].geometry.scale).toHaveBeenCalledWith(planet1Scale, planet1Scale, planet1Scale);
    expect(meshMocks[planet2Index].geometry.scale).toHaveBeenCalledWith(planet2Scale, planet2Scale, planet2Scale);
  });
});
