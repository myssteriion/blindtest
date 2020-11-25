import { UtilsService } from "../../src/app/services/utils.service";
import { BASE64, CommonUtilsService, DATA } from "myssteriion-utils";
import { AVATAR_NOT_FOUND } from "../../src/app/tools/constant";
import { Avatar } from "../../src/app/interfaces/entity/avatar.interface";
import { Music } from "../../src/app/interfaces/entity/music.interface";

describe("UtilsService", () => {
	
	let service: UtilsService;
	
	
	
	beforeAll(() => {
		service = new UtilsService( new CommonUtilsService() );
	});
	
	
	
	it("should be return AVATAR_NOT_FOUND (getImgFromAvatar)", () => {
		
		expect( service.getImgFromAvatar(undefined) ).toEqual(AVATAR_NOT_FOUND);
		expect( service.getImgFromAvatar(null) ).toEqual(AVATAR_NOT_FOUND);
		
		let avatar: Avatar = { name: "avatarName" };
		expect( service.getImgFromAvatar(avatar) ).toEqual(AVATAR_NOT_FOUND);
		
		avatar = { name: "avatarName", flux: { name: "fluxName", fileExists: undefined, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getImgFromAvatar(avatar) ).toEqual(AVATAR_NOT_FOUND);
		
		avatar = { name: "avatarName", flux: { name: "fluxName", fileExists: null, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getImgFromAvatar(avatar) ).toEqual(AVATAR_NOT_FOUND);
		
		avatar = { name: "avatarName", flux: { name: "fluxName", fileExists: false, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getImgFromAvatar(avatar) ).toEqual(AVATAR_NOT_FOUND);
	});
	
	it("should be return flux (getImgFromAvatar)", () => {
		
		let avatar: Avatar = { name: "avatarName", flux: { name: "fluxName", fileExists: true, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getImgFromAvatar(avatar) ).toEqual(DATA + "contentFlux" + BASE64 + "contentType");
	});
	
	
	it("should be return null (getAudioFromMusic)", () => {
		
		expect( service.getAudioFromMusic(undefined) ).toBeNull();
		expect( service.getAudioFromMusic(null) ).toBeNull();
		
		let music: Music = { name: "musicName", theme: Theme.ANNEES_60 };
		expect( service.getAudioFromMusic(music) ).toBeNull();
		
		music = { name: "musicName", theme: Theme.ANNEES_60, flux: { name: "fluxName", fileExists: undefined, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getAudioFromMusic(music) ).toBeNull();
		
		music = { name: "musicName", theme: Theme.ANNEES_60, flux: { name: "fluxName", fileExists: null, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getAudioFromMusic(music) ).toBeNull();
		
		music = { name: "musicName", theme: Theme.ANNEES_60, flux: { name: "fluxName", fileExists: false, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getAudioFromMusic(music) ).toBeNull();
	});
	
	it("should be return flux (getAudioFromMusic)", () => {
		
		let music = { name: "musicName", theme: Theme.ANNEES_60, flux: { name: "fluxName", fileExists: undefined, contentFlux: "contentFlux", contentType: "contentType" } };
		expect( service.getAudioFromMusic(music) ).toEqual(DATA + "contentFlux" + BASE64 + "contentType");
	});
	
	
});
