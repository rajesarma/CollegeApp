package in.education.college.user;

import in.education.college.converter.UserConverter;
import in.education.college.dto.UserDto;
import in.education.college.model.Role;
import in.education.college.model.User;
import in.education.college.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	UserController(UserService userService) {
		this.userService = userService;
	}

	@Value("${save}")
	private String save;

	@Value("${change}")
	private String change;

	@Value("${update}")
	private String update;

	private String userDataDto = "userDto";
	private String usersList = "usersList";
	private String buttonValue = "buttonValue";
	private String action = "action";
	private String method = "method";
	private String roles = "roles";
	private String message;
	private String msg = "message";
	private String selectedRoleIds = "selectedRoleIds";

	@GetMapping("/admin/user/add")
	public ModelAndView save() {

		ModelAndView mav = new ModelAndView("user", userDataDto, new UserDto());
		mav.addObject(buttonValue, save );
		mav.addObject(action,"/admin/user/add");
		mav.addObject(method,"Post");
		mav.addObject(roles, userService.getRoles());
		return mav;
	}

	@PostMapping("/admin/user/add")
	public ModelAndView save(@ModelAttribute UserDto userDto,
			BindingResult bindingResult) { // @Valid

		new UserValidator().validate(userDto, bindingResult);
		ModelAndView mav = new ModelAndView("user", userDataDto, userDto);

		if(!bindingResult.hasErrors()) {
			Optional<UserDto> savedUserDto = userService.save(userDto);

			if (!savedUserDto.isPresent()) {
				message = "User already existed";
			} else {
				userDto.setUsername("");
				userDto.setPassword("");
				userDto.setEmail("");
				userDto.setUserDesc("");
				message = "User Information inserted successfully";
			}
		} else {
			message = "User Information is not inserted";
			mav.addObject(selectedRoleIds, getSelectedRoles(userDto.getRoles()));
		}

		mav.addObject(msg, message);
		mav.addObject(buttonValue, save );
		mav.addObject(action,"/admin/user/add");
		mav.addObject(method,"Post");
		mav.addObject(roles, userService.getRoles());
		return mav;
	}

	@GetMapping("/changePassword")
	public ModelAndView change() {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ModelAndView mav = new ModelAndView("changePassword", userDataDto, UserConverter.convert(user));
		mav.addObject(buttonValue, change );
		mav.addObject(action,"/changePassword");
		mav.addObject(method,"Post");

		return mav;
	}

	@PostMapping("/changePassword")
	public ModelAndView change(@ModelAttribute UserDto userDto,
			@RequestParam String newPassword) {

		ModelAndView mav = new ModelAndView("changePassword", userDataDto, userDto);
		mav.addObject(buttonValue, change );
		mav.addObject(action,"/changePassword");
		mav.addObject(msg, "Problem in Updating Password");
		mav.addObject(method,"Post");

		if(StringUtils.isNotEmpty(newPassword)) {
			User LoggedInUser =
					(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<UserDto> userOptional =
					userService.changePassword(LoggedInUser.getUsername(),
							userDto.getPassword(), newPassword);

			if(userOptional.isPresent() && userOptional.get().getUserId() > 0) {
				mav.addObject(msg, "Password Updated");
			}
		}
		return mav;
	}

	@GetMapping("/admin/usersList")
	public ModelAndView list() {

		ModelAndView mav = new ModelAndView(usersList, userDataDto, new UserDto());

		mav.addObject(buttonValue, "Get" );
		mav.addObject(action,"/admin/usersList");
		mav.addObject(method,"post");
		mav.addObject(roles, userService.getRoles());
		return mav;
	}

	@PostMapping("/admin/usersList")
	public ModelAndView list(@ModelAttribute("userDto") UserDto userDto) {

		ModelAndView mav = new ModelAndView(usersList, userDataDto, userDto);
		mav.addObject(buttonValue, "Get" );
		mav.addObject(action,"/admin/usersList");
		mav.addObject(method,"Post");
		mav.addObject("Role", "/admin");
		mav.addObject(usersList, userService.findUsersByRoles(userDto));
		mav.addObject(selectedRoleIds, getSelectedRoles(userDto.getRoles()));
		mav.addObject(roles, userService.getRoles());
		return mav;
	}

	@GetMapping("/admin/user/edit/{userId}/{operation}")
	public ModelAndView edit(@PathVariable("userId") long userId,
			@PathVariable("operation") String operation) {

		ModelAndView mav = new ModelAndView("user", userDataDto, new UserDto());

		Optional<UserDto> userOptional = userService.findUsersById(userId);
		if(userOptional.isPresent()) {
			UserDto userDto = userOptional.get();
			mav.addObject(userDataDto, userDto);
			mav.addObject(selectedRoleIds, getSelectedRoles(userDto.getRoles()));
		}

		mav.addObject(roles, userService.getRoles());
		mav.addObject(buttonValue, operation.toUpperCase());
		mav.addObject(action,"/admin/user/" + operation); // operation as Update/Delete
		mav.addObject(method,"post");
		mav.addObject("Role", "/admin");
		return mav;
	}

	@PostMapping("/admin/user/delete")
	public ModelAndView delete(@ModelAttribute("userDto") UserDto userDto)  {

		ModelAndView mav = new ModelAndView(usersList, userDataDto, userDto);
		Optional<UserDto> userOptional = userService.delete(userDto);

		if(!userOptional.isPresent()) {
			message = "User Information deleted successfully";
		} else {
			message = "Problem in User deletion";
		}

		mav.addObject(msg, message);
		mav.addObject(buttonValue, "Get" );
		mav.addObject(action,"/admin/usersList");
		mav.addObject(method,"Post");
		mav.addObject("Role", "/admin");
		mav.addObject(usersList, userService.findUsersByRoles(userDto));
		mav.addObject(roles, userService.getRoles());
		mav.addObject(selectedRoleIds, getSelectedRoles(userDto.getRoles()));

		return mav;
	}

	@PostMapping("/admin/user/update")
	public ModelAndView update(@ModelAttribute UserDto userDto,
			BindingResult bindingResult) { // @Valid

		new UserValidator().validate(userDto, bindingResult);

		ModelAndView mav = new ModelAndView(usersList, userDataDto, userDto);
		mav.addObject(selectedRoleIds, getSelectedRoles(userDto.getRoles()));
		mav.addObject(method,"Post");
		mav.addObject(roles, userService.getRoles());

		if(bindingResult.hasErrors()) {
			mav.setViewName("user");
			mav.addObject(msg, "User Information is not updated");
			mav.addObject(buttonValue, update );
			mav.addObject(action,"/admin/user/update");
			return mav;

		} else {
			Optional<UserDto> userOptional = userService.update(userDto);

			if (userOptional.isPresent()) {
				message = "User Information updated successfully";
				mav.addObject(userDataDto, userOptional.get());
			} else {
				userDto.setPassword("");
				message = "User Information is not updated";
			}
		}

		mav.addObject(msg, message);
		mav.addObject(buttonValue, "Get" );
		mav.addObject(action,"/admin/usersList");
		mav.addObject("Role", "/admin");

		mav.addObject(usersList, userService.findUsersByRoles(userDto));
		mav.addObject(selectedRoleIds, getSelectedRoles(userDto.getRoles()));

		return mav;
	}

	private List<Long> getSelectedRoles(List<Role> roles) {
		return roles
				.stream()
				.map(Role::getRoleId)
				.distinct()
				.collect(Collectors.toList());
	}
}

/*Map<Long, String> selectedRoles = user.getRoles()
					.stream()
					.collect(Collectors.toMap(Role::getRoleId, Role::getRoleName, (a1,
							a2) -> a1, LinkedHashMap::new));
			mav.addObject("selectedRoles", selectedRoles);*/

/*mav.addObject("roles",
				StreamSupport.stream(roleRepository.findAll().spliterator(), false)
						.collect(Collectors.toMap(Role::getRoleId, Role::getRoleName)));*/

/*user.getRoles()
					.stream()
					.collect(Collectors.toMap(Role::getRoleId, Role::getRoleName))
					.forEach((k,v) -> System.out.println(k+" : "+v));

			Map<Long, String> selectedRoles = user.getRoles()
										.stream()
										.collect(Collectors.toMap(Role::getRoleId, Role::getRoleName));*/

/*userData.getRoles()
				.stream()
				.map(Role :: getRoleId)
				.forEach(role -> System.out.println(role+ ", "));*/