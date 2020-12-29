import { BASE64, CommonUtilsService, DATA, Flux } from "myssteriion-utils";
import { Theme } from "../../src/app/interfaces/common/theme.enum";
import { Avatar } from "../../src/app/interfaces/entity/avatar";
import { Music } from "../../src/app/interfaces/entity/music";
import { UtilsService } from "../../src/app/services/utils.service";
import { AVATAR_NOT_FOUND } from "../../src/app/tools/constant";

describe("UtilsService", () => {
	
	let service: UtilsService;
	
	
	
	beforeAll(() => {
		service = new UtilsService( new CommonUtilsService() );
	});
	
	
	
	it("should be return AVATAR_NOT_FOUND (getImgFromAvatar)", () => {
		
		let avatar = new Avatar();
		avatar.name = "avatarName";
		expect( service.getImgFromAvatar(avatar) ).toEqual(AVATAR_NOT_FOUND);
		
		avatar = new Avatar();
		avatar.name = "avatarName";
		avatar.flux = new Flux();
		avatar.flux.name = "fluxName";
		avatar.flux.contentFlux = "contentFlux";
		avatar.flux.contentType = "contentType";
		expect( service.getImgFromAvatar(avatar) ).toEqual(AVATAR_NOT_FOUND);
		
		avatar = new Avatar();
		avatar.name = "avatarName";
		avatar.flux = new Flux();
		avatar.flux.name = "fluxName";
		avatar.flux.fileExists = false;
		avatar.flux.contentFlux = "contentFlux";
		avatar.flux.contentType = "contentType";
		expect( service.getImgFromAvatar(avatar) ).toEqual(AVATAR_NOT_FOUND);
	});
	
	it("should be return flux (getImgFromAvatar)", () => {
		
		let avatar = new Avatar();
		avatar.name = "avatarName";
		avatar.flux = new Flux();
		avatar.flux.name = "fluxName";
		avatar.flux.fileExists = true;
		avatar.flux.contentFlux = "contentFlux";
		avatar.flux.contentType = "contentType";
		expect( service.getImgFromAvatar(avatar) ).toEqual(DATA + "contentFlux" + BASE64 + "contentType");
	});
	
	
	it("should be return null (getAudioFromMusic)", () => {
		
		let music = new Music();
		music.name = "musicName";
		music.theme = Theme.ANNEES_60;
		expect( service.getAudioFromMusic(music) ).toBeNull();
		
		music = new Music();
		music.name = "musicName";
		music.theme = Theme.ANNEES_60;
		music.flux = new Flux();
		music.flux.name = "fluxName";
		music.flux.contentFlux = "contentFlux";
		music.flux.contentType = "contentType";
		expect( service.getAudioFromMusic(music) ).toBeNull();
		
		music = new Music();
		music.name = "musicName";
		music.theme = Theme.ANNEES_60;
		music.flux = new Flux();
		music.flux.name = "fluxName";
		music.flux.fileExists = false;
		music.flux.contentFlux = "contentFlux";
		music.flux.contentType = "contentType";
		expect( service.getAudioFromMusic(music) ).toBeNull();
	});
	
	it("should be return flux (getAudioFromMusic)", () => {
		
		let music = new Music();
		music.name = "musicName";
		music.theme = Theme.ANNEES_60;
		music.flux = new Flux();
		music.flux.name = "fluxName";
		music.flux.fileExists = true;
		music.flux.contentFlux = "contentFlux";
		music.flux.contentType = "contentType";
		expect( service.getAudioFromMusic(music) ).toEqual(DATA + "contentFlux" + BASE64 + "contentType");
	});
	
});
