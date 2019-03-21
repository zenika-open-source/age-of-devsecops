import * as THREE from 'three';

export default interface Planet {
  name: string;
  rotationSpeed: number;
  axialTilt: number;
  textureName: string;
  texture?: THREE.Texture;
  mesh?: THREE.Mesh;
  radius?: number;
}
