import {Component, ElementRef, Inject, Input, OnInit} from '@angular/core';
import {RenderFunction} from './render-function';
import PlayerScore from '../player-score';
import {BehaviorSubject} from 'rxjs';
import Planet from './planet';
import {asPercentageOfMinMaxInterval} from '../utils/math.utils';
import {toSnakeCase} from '../text-utils';

@Component({
  selector: 'app-planets',
  templateUrl: './planets.component.html',
  styleUrls: ['./planets.component.scss']
})
export class PlanetsComponent implements OnInit {

  private planets: Array<Planet> = [
    {
      name: 'ophore',
      rotationSpeed: 0.3945309270155865,
      axialTilt: -0.03626422727142957,
      textureName: 'red1',
    },
    // {
    //   name: 'aneitov',
    //   rotationSpeed: 0.49234713623319415,
    //   axialTilt: -0.18116248351461064,
    //   textureName: 'red2',
    // },
    // {
    //   name: 'akinez',
    //   rotationSpeed: -0.43867164995621394,
    //   axialTilt: -0.22121500328951274,
    //   textureName: 'red5',
    // },
    // {
    //   name: 'dakonus',
    //   rotationSpeed: -0.3788718109915625,
    //   axialTilt: -0.24834892813394926,
    //   textureName: 'red4',
    // },
    // {
    //   name: 'piwei',
    //   rotationSpeed: 0.31785446609148954,
    //   axialTilt: -0.027467180821007187,
    //   textureName: 'red3',
    // },
    // {
    //   name: 'ogophus',
    //   rotationSpeed: -0.2908870596203389,
    //   axialTilt: 0.43114138175133815,
    //   textureName: 'yellow2',
    // },
    // {
    //   name: 'tivilles',
    //   rotationSpeed: 0.48742973273834095,
    //   axialTilt: -0.3550096771796551,
    //   textureName: 'yellow1',
    // },
    {
      name: 'thunides',
      rotationSpeed: -0.3240427170641214,
      axialTilt: -0.12736023442379502,
      textureName: 'yellow3',
    },
    // {
    //   name: 'kaiphus',
    //   rotationSpeed: -0.2727908758556006,
    //   axialTilt: -0.17817159344284492,
    //   textureName: 'yellow5',
    // },
    // {
    //   name: 'kinziri',
    //   rotationSpeed: 0.2644741001344519,
    //   axialTilt: -0.4081936697732515,
    //   textureName: 'yellow4',
    // },
    // {
    //   name: 'niborus',
    //   rotationSpeed: -0.3412508839644845,
    //   axialTilt: 0.3706423958055325,
    //   textureName: 'grey5',
    // },
    {
      name: 'luuria',
      rotationSpeed: 0.2924641279233148,
      axialTilt: -0.04160167022258197,
      textureName: 'grey1',
    },
    // {
    //   name: 'trioteru',
    //   rotationSpeed: 0.3389695187689985,
    //   axialTilt: -0.43773015649285113,
    //   textureName: 'grey2',
    // },
    // {
    //   name: 'strooter',
    //   rotationSpeed: -0.2515156811666817,
    //   axialTilt: -0.3069633258594837,
    //   textureName: 'grey3',
    // },
    // {
    //   name: 'tesade',
    //   rotationSpeed: 0.30944187133161405,
    //   axialTilt: -0.3846895864195807,
    //   textureName: 'grey4',
    // },
    {
      name: 'dragano',
      rotationSpeed: -0.3695686428641449,
      axialTilt: -0.4027302234653185,
      textureName: 'blue1',
    },
    // {
    //   name: 'azuno',
    //   rotationSpeed: 0.30402275618127245,
    //   axialTilt: 0.1487478289315501,
    //   textureName: 'blue5',
    // },
    // {
    //   name: 'carebos',
    //   rotationSpeed: 0.4775142841076474,
    //   axialTilt: -0.0621675420434841,
    //   textureName: 'blue2',
    // },
    // {
    //   name: 'obade',
    //   rotationSpeed: 0.4080657581718037,
    //   axialTilt: 0.46515450061605806,
    //   textureName: 'blue3',
    // },
    // {
    //   name: 'astriturn',
    //   rotationSpeed: -0.4629108801170201,
    //   axialTilt: -0.08842668352744716,
    //   textureName: 'blue4',
    // },
    // {
    //   name: 'zatruna',
    //   rotationSpeed: -0.4227526732727597,
    //   axialTilt: 0.07118156411144938,
    //   textureName: 'green1',
    // },
    // {
    //   name: 'votis',
    //   rotationSpeed: 0.290385294274052,
    //   axialTilt: -0.23268556177063404,
    //   textureName: 'green2',
    // },
    // {
    //   name: 'xenvion',
    //   rotationSpeed: 0.36557440606186115,
    //   axialTilt: -0.11235154915524276,
    //   textureName: 'green3',
    // },
    {
      name: 'pucara',
      rotationSpeed: -0.35903715947203885,
      axialTilt: -0.1993774478298711,
      textureName: 'green4',
    },
    // {
    //   name: 'estea',
    //   rotationSpeed: 0.2885258523557898,
    //   axialTilt: 0.3746010756695491,
    //   textureName: 'green5',
    // }
  ];

  private container: HTMLElement;
  private containerWidth: number;
  private containerHeight: number;
  private containerAspectRatio: number;

  private planetDistanceX;
  private planetDistanceY;
  private planetMaxRadius = 0.3;
  private planetMinRadius = 0.09;
  private planetRadiusDiff = this.planetMaxRadius - this.planetMinRadius;

  private font = null;
  private fontSize = 0.12;

  private renderFunctions: Array<RenderFunction> = [];
  private _playerScores = new BehaviorSubject<Array<PlayerScore>>([]);

  constructor(
    private root: ElementRef,
    @Inject('THREE') private THREE,
    @Inject('requestAnimationFrame') private requestAnimationFrame,
  ) {
  }

  @Input()
  set playerScores(playerScores: Array<PlayerScore>) {
    this._playerScores.next(playerScores);
  }

  ngOnInit() {
    this.container = this.root.nativeElement;
    this.containerWidth = this.container.offsetWidth;
    this.containerHeight = this.container.offsetHeight;
    this.containerAspectRatio = this.containerWidth / this.containerHeight;

    this.planetDistanceX = 1.3;
    this.planetDistanceY = this.planetDistanceX / this.containerAspectRatio;

    this.loadAssets()
      .then(() => this.render())
      .then(() => this._playerScores.subscribe(playerScores => this.adjustPlanetsSize(playerScores)));
  }

  private loadAssets(): Promise<void> {
    return new Promise((resolve, reject) => {
      const loadingManager = new this.THREE.LoadingManager(resolve, () => {
      }, reject);

      const fontLoader = new this.THREE.FontLoader(loadingManager);
      const textureLoader = new this.THREE.TextureLoader(loadingManager);

      // fontLoader.load('assets/fonts/ZCOOL_QingKe_HuangYou_Regular.json', result => this.font = result);
      fontLoader.load('assets/fonts/Retron2000_Regular.json', result => this.font = result);
      this.planets.forEach(planet => {

        textureLoader.load(`assets/images/planets/${planet.textureName}.jpg`, result => {
          planet.texture = result;
          planet.texture.minFilter = this.THREE.LinearFilter;
        });
      });
    });
  }

  private render() {
    const renderer = new this.THREE.WebGLRenderer({
      antialias: true,
      alpha: true
    });
    renderer.setSize(this.containerWidth, this.containerHeight);
    this.container.appendChild(renderer.domElement);

    const scene = new this.THREE.Scene();
    const camera = this.initCamera();
    this.renderFunctions.push(() => {
      renderer.render(scene, camera);
    });

    this.renderPlanets(scene);

    this.renderLights(scene);

    // Rendering loop
    const targetFps = 30;
    const fpsInterval = 1000 / targetFps;
    let lastRender = null;
    const animate = now => {
      const delta = now - lastRender;
      if (delta >= fpsInterval) {
        lastRender = now - (delta % fpsInterval);
        this.renderFunctions.forEach(updateFn => updateFn(delta / 1000));
      }
      // Infinite loop
      this.requestAnimationFrame(animate);
    };
    this.requestAnimationFrame(animate);
  }

  private initCamera() {
    const cameraHalfWidth = 2.6 * this.planetDistanceX;
    const cameraHalfHeight = 2.6 * this.planetDistanceY;
    const camera = new this.THREE.OrthographicCamera(-cameraHalfWidth, cameraHalfWidth, cameraHalfHeight, -cameraHalfHeight,
      0.01, 100);
    camera.position.set(2 * this.planetDistanceX, 2 * this.planetDistanceY, 1);
    return camera;
  }

  private renderLights(scene) {
    const light1 = new this.THREE.AmbientLight(0x666666);
    const light2 = new this.THREE.DirectionalLight(0xeeeeee, 1);
    light2.position.set(5, 5, 5);
    scene.add(light1);
    scene.add(light2);
  }

  private renderPlanets(scene) {
    // for (let i = 0; i < 25; i++) {
    //   const planet = this.planets[i];
    //
    //   this.renderPlanet(scene, planet, this.planetDistanceX * (i % 5), this.planetDistanceY * Math.floor(i / 5));
    // }

    this.renderPlanet(scene, this.planets[0], this.planetDistanceX * 1, this.planetDistanceY * 3.4);
    this.renderPlanet(scene, this.planets[1], this.planetDistanceX * 2.4, this.planetDistanceY * 2.9);
    this.renderPlanet(scene, this.planets[2], this.planetDistanceX * 0.2, this.planetDistanceY * 2.3);
    this.renderPlanet(scene, this.planets[3], this.planetDistanceX * 3.7, this.planetDistanceY * 1.7);
    this.renderPlanet(scene, this.planets[4], this.planetDistanceX * 2.3, this.planetDistanceY * 0.8);
  }

  private renderPlanet(scene, planet, x, y) {
    this.renderPlanetShape(scene, planet, x, y);
    this.renderPlanetName(scene, planet, x, y);
  }

  private renderPlanetShape(scene, planet, x, y) {
    const radius = this.planetMaxRadius;
    const planetGeometry = new this.THREE.SphereGeometry(radius, 32, 32);
    const planetMaterial = new this.THREE.MeshPhongMaterial({
      map: planet.texture,
      shininess: 10
    });

    const planetMesh = new this.THREE.Mesh(planetGeometry, planetMaterial);
    scene.add(planetMesh);
    planetMesh.position.set(x, y + this.fontSize, 0);
    planetMesh.rotateZ(planet.axialTilt);

    planet.mesh = planetMesh;
    planet.radius = radius;
    this.renderFunctions.push(delta => planetMesh.rotateY(planet.rotationSpeed * delta));
  }

  private renderPlanetName(scene, planet, x, y) {
    const textGeometry = new this.THREE.TextGeometry(toSnakeCase(planet.name), {
      font: this.font,
      size: this.fontSize,
      height: 0.01,
    });
    textGeometry.center();
    const textMaterial = new this.THREE.MeshBasicMaterial({color: 0xffffff});
    const textMesh = new this.THREE.Mesh(textGeometry, textMaterial);
    scene.add(textMesh);
    textMesh.position.set(x, y - this.planetMaxRadius, 0);
  }

  private adjustPlanetsSize(playerScores: Array<PlayerScore>) {
    if (playerScores) {
      const scoreByPlayer = new Map<string, number>(playerScores.map(a => [a.playerId, a.score] as [string, number]));
      const planetScores = this.planets.map(planet => scoreByPlayer.get(planet.name));
      const percentages = asPercentageOfMinMaxInterval(planetScores);
      this.planets.forEach((planet, planetIndex) => this.adjustPlanetSize(planet, percentages[planetIndex]));
    }
  }

  private adjustPlanetSize(planet, percentage) {
    if (percentage == null) {
      planet.mesh.visible = false;
      return;
    }
    planet.mesh.visible = true;
    const planetGeometry = planet.mesh.geometry;
    const newRadius = this.planetMinRadius + percentage * this.planetRadiusDiff;
    const newScale = newRadius / planet.radius;
    planetGeometry.scale(newScale, newScale, newScale);
    planet.radius = newRadius;
  }

}
